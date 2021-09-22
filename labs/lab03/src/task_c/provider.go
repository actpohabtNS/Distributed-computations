package main

import "fmt"

func (t *table) Provider() {
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

	fmt.Printf("[Provider]: I have provided %s\n", strVal)
	fmt.Println(t)
	t.mu.Unlock()
}
