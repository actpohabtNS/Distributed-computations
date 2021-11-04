package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func randomInit() {
	rand.Seed(time.Now().UnixNano())
}

func randomInt(min, max int) int {
	return rand.Intn(max-min+1) + min
}

type Lot struct {
	name  		string
	startPrice 	int
}

type Bid struct {
	from		string
	bid			int
}

type Auction struct {
	mu				sync.Mutex
	lots       		[]Lot
	highestBid 		Bid
	bidCh      		chan Bid
	winBidThreshold time.Duration //seconds
	participants	[]Participant
}

func (auc *Auction) serveLot(lot Lot) {
	winnerFound := false

	fmt.Println("==================================")
	fmt.Printf("< Bidding started on lot %s >\n\n", lot.name)
	auc.startBidders(lot)

	for !winnerFound {
		select {
		case newBid := <- auc.bidCh:
			auc.highestBid = newBid
		case <- time.After(auc.winBidThreshold):
			winnerFound = true
		}
	}
	fmt.Printf("\n< Lot %s sold to %s at price %d >\n", lot.name, auc.highestBid.from, auc.highestBid.bid)
	fmt.Println("==================================")
	auc.stopBidders()

	time.Sleep(1 * time.Second)
}

func (auc *Auction) start() {
	for _, lot := range auc.lots {
		auc.highestBid = Bid{"initial", lot.startPrice}
		auc.serveLot(lot)
	}

	fmt.Println("==================================")
	fmt.Println("< Auction ended! >")
}

func (auc *Auction) startBidders(lot Lot) {
	for _, part := range auc.participants {
		go part.start(lot)
	}
}

func (auc *Auction) stopBidders() {
	for _, part := range auc.participants {
		part.stopCh <- true
	}
}

type Participant struct {
	Auction       *Auction
	name          string
	waitThreshold time.Duration //seconds
	bidThreshold  int
	money         int
	stopCh		  chan bool
}

func newParticipant(auc *Auction) Participant {
	pCount := len(auc.participants)
	newPart := Participant{
		Auction:       auc,
		name:          fmt.Sprintf("Participant %d", pCount),
		waitThreshold: time.Duration(randomInt(750, 1250) * int(time.Millisecond)) ,
		bidThreshold:  randomInt(5, 100),
		money:         randomInt(100, 1000),
		stopCh:        make(chan bool),
	}
	auc.participants = append(auc.participants, newPart)
	return newPart
}

func (p Participant) bid(lot Lot) bool {
	newBid := p.Auction.highestBid.bid + p.bidThreshold
	canBid := newBid <= p.money
	willBid := false
	if canBid {

		if p.Auction.highestBid.from == p.name {
			fmt.Printf("%s: won't bid, I have the highest bid already!\n", p.name)
		} else {

			fmt.Printf("%s: bidding %d on %s\n", p.name, newBid,
				lot.name)
			p.Auction.bidCh <- Bid {
				from:	p.name,
				bid: 	p.Auction.highestBid.bid + p.bidThreshold,
			}
			willBid = true
		}

	} else {
		fmt.Printf("%s: not enough money to bid on %s\n", p.name,
			lot.name)
	}
	return willBid
}

func (p Participant) start(lot Lot) {
	for {
		select {
		case <-p.stopCh:
			fmt.Printf("%s stopping...\n", p.name)
			return
		default:
			canBid := p.bid(lot)
			if !canBid {
				select {
				case <- p.stopCh:
					fmt.Printf("%s stopping...\n", p.name)
					return
				}
			}
		}

		time.Sleep(p.waitThreshold)
	}
}

func main() {
	randomInit()

	lots := []Lot{{"Old chair", 100}, {"Old vase", 150}}

	auc := Auction{
		lots: lots,
		bidCh: make(chan Bid),
		winBidThreshold: time.Second * 3,
	}

	for i := 0; i < 5; i++ {
		newParticipant(&auc)
	}

	auc.start()
}
