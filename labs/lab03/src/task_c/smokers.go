package main

import (
	"fmt"
)

func (t *table) SmokerWithTobacco(smokers chan int) {
	for {
		select {
		case <-smokers:
			if t.paper == 0 || t.matches == 0 {
				fmt.Println("[Smoker with Tobacco]: nothing to smoke")
				continue
			}

			t.mu.Lock()
			t.paper--
			t.matches--
			t.mu.Unlock()
			fmt.Println("[Smoker with Tobacco]: smoking (̅_̅_̅_̅(̅_̅_̅_̅_̅_̅_̅_̅_̅̅_̅()ڪے")
		}
	}
}

func (t *table) SmokerWithPaper(smokers chan int) {
	for {
		select {
		case <-smokers:
			if t.tobacco == 0 || t.matches == 0 {
				fmt.Println("[Smoker with Paper]: nothing to smoke")
				continue
			}

			t.mu.Lock()
			t.tobacco--
			t.matches--
			t.mu.Unlock()
			fmt.Println("[Smoker with Paper]: smoking (̅_̅_̅_̅(̅_̅_̅_̅_̅_̅_̅_̅_̅̅_̅()ڪے")
		}
	}
}

func (t *table) SmokerWithMatches(smokers chan int) {
	for {
		select {
		case <-smokers:
			if t.tobacco == 0 || t.paper == 0 {
				fmt.Println("[Smoker with Matches]: nothing to smoke")
				continue
			}

			t.mu.Lock()
			t.tobacco--
			t.paper--
			t.mu.Unlock()
			fmt.Println("[Smoker with Matches]: smoking (̅_̅_̅_̅(̅_̅_̅_̅_̅_̅_̅_̅_̅̅_̅()ڪے")
		}
	}
}
