package main

import (
	"time"
)

const CycleTime = 1000 * time.Millisecond

func main() {
	randomInit()

	t := table{}
	ch := make(chan int)

	go t.SmokerWithTobacco(ch)
	go t.SmokerWithPaper(ch)
	go t.SmokerWithMatches(ch)

	t.Provider(ch, 3)
}
