package main

import (
	"fmt"
	"sync"
)

type table struct {
	mu      sync.Mutex
	tobacco int
	paper   int
	matches int
}

func (t *table) tableAdd(amount int) {
	t.tobacco += amount
	t.paper += amount
	t.matches += amount
}

func (t *table) String() string {
	return fmt.Sprintf("At the table now: %d tobaccos, %d paper and %d matches", t.tobacco, t.paper, t.matches)
}
