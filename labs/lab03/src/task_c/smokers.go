package main

import (
	"fmt"
)

//TODO: smokers should decrement ss values, should use semaphores

func (t *table) SmokerWithTobacco() {
	if t.tobacco != 0 ||
		t.paper == 0 ||
		t.matches == 0 {
		fmt.Println("[Smoker with Tobacco]: Why am I receiving a wrong Smoking Set???")
	}

	t.mu.Lock()
	t.paper--
	t.matches--
	t.mu.Unlock()
	fmt.Println("[Smoker with Tobacco]: smoking...")
}

func (t *table) SmokerWithPaper() {
	if t.tobacco != 1 ||
		t.paper != 0 ||
		t.matches != 1 {
		fmt.Println("[Smoker with Paper]: Why am I receiving a wrong Smoking Set???")
	}

	t.mu.Lock()
	t.tobacco--
	t.matches--
	t.mu.Unlock()
	fmt.Println("[Smoker with Paper]: smoking...")
}

func (t *table) SmokerWithMatches() {
	if t.tobacco != 1 ||
		t.paper != 1 ||
		t.matches != 0 {
		fmt.Println("[Smoker with Matches]: Why am I receiving a wrong Smoking Set???")
	}

	t.mu.Lock()
	t.tobacco--
	t.paper--
	t.mu.Unlock()
	fmt.Println("[Smoker with Matches]: smoking...")
}
