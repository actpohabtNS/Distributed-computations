package main

import (
	"fmt"
	"math/rand"
	"time"
)

func randomInit() {
	rand.Seed(time.Now().UnixNano())
}

func randomInt(min, max int) int {
	return rand.Intn(max-min+1) + min
}

type Lot struct {
	name  string
	price int
}

type Auction struct {
	lots       []Lot
	highestBid int
	bidCh      chan int
	pCount     int
}

type Participant struct {
	Auction       Auction
	name          string
	waitThreshold int
	bidThreshold  int
	money         int
}

func newParticipant(auc Auction) Participant {
	auc.pCount++
	return Participant{
		Auction:       auc,
		name:          fmt.Sprintf("Participant %d", auc.pCount),
		waitThreshold: randomInt(500, 1000),
		bidThreshold:  randomInt(5, 100),
		money:         randomInt(100, 1000),
	}
}

func (p Participant) bid() bool {
	newBid := p.Auction.highestBid + p.bidThreshold
	willBid := newBid <= p.money
	if willBid {
		p.Auction.bidCh <- p.Auction.highestBid + p.bidThreshold
	}
	return willBid
}

func main() {
	println("Hello")
}
