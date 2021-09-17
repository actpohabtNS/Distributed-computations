package main

import "fmt"

type Monk struct {
	monast int
	qi     int
}

func (m Monk) String() string {
	return fmt.Sprintf("[M%d] qi %d", m.monast, m.qi)
}
