package main

import (
	"math/rand"
	"time"
)

func randomInit() {
	rand.Seed(time.Now().UnixNano())
}

func randomInt(min, max int) int {
	return rand.Intn(max-min+1) + min
}
