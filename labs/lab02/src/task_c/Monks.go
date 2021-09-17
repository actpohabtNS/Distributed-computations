package main

import (
	"fmt"
	"time"
)

const MonastSize = 100000
const MinQi = 10
const MaxQi = MonastSize * 10

func main() {
	randomInit()
	start := time.Now()

	monks := make([]Monk, MonastSize*2)
	initMonasteries(monks)

	fmt.Println(startContest(monks))
	fmt.Printf("Time elapsed: %d micros\n", time.Since(start).Microseconds())
}

func initMonasteries(monks []Monk) {
	for i := 0; i < len(monks); i++ {
		monast := 1
		if i >= len(monks)/2 {
			monast = 2
		}
		monks[i] = Monk{monast, randomInt(MinQi, MaxQi)}
	}
}

func startContest(monks []Monk) Monk {
	ch := make(chan Monk)
	go _strRec(monks, 0, MonastSize*2-1, ch)
	return <-ch
}

func _strRec(monks []Monk, lo, hi int, ch chan Monk) {
	if lo == hi {
		ch <- monks[lo]
		return
	}

	ch1, ch2 := make(chan Monk), make(chan Monk)
	go _strRec(monks, lo, (lo+hi)/2, ch1)
	go _strRec(monks, (lo+hi)/2+1, hi, ch2)
	monkLeft, monkRight := <-ch1, <-ch2

	if monkLeft.qi > monkRight.qi {
		ch <- monkLeft
	} else {
		ch <- monkRight
	}
}
