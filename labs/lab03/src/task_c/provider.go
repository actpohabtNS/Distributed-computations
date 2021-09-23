package main

import (
	"fmt"
	"time"
)

func (t *table) Provider(smokers chan int, subscribersAmount int) {
	for {
		t.mu.Lock()
		t.tableAdd(1)
		exclude := randomInt(0, 2)

		var strVal string

		switch exclude {
		case 0:
			t.tobacco--
			strVal = "Paper, Matches"
		case 1:
			t.paper--
			strVal = "Tobacco, Matches"
		case 2:
			t.matches--
			strVal = "Tobacco, Paper"
		}

		fmt.Println()
		fmt.Println("————————————————————————————————————————————")
		fmt.Println()
		fmt.Printf("[Provider]: I have provided %s\n", strVal)
		fmt.Println(t)
		fmt.Println()
		t.mu.Unlock()
		for i := 0; i < subscribersAmount; i++ {
			smokers <- 1
		}

		time.Sleep(CycleTime)
	}
}
