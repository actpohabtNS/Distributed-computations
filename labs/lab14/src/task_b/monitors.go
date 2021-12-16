package main

import (
	"fmt"
	"os"
	"sync"
	"time"
)

func Monitor1(garden [][]bool, rwMutex *sync.RWMutex, exit chan int) {

	file, err := os.Create("D:/Files/university/study/year_3/distributed-computations/labs/lab04/src/task_b/gardenStatus.txt")

	if err != nil {
		fmt.Println("Error while creating file:", err)
		return
	}

	defer func(file *os.File) {
		err := file.Close()
		if err != nil {

		}
	}(file)

	for i := 0; i < MaxActions; i++ {
		rwMutex.RLock()
		fmt.Println("Saving plants' status")
		for i := 0; i < len(garden); i++ {
			var line string
			for j := 0; j < len(garden[0]); j++ {
				line += boolToString(garden[i][j]) + " "
			}
			_, _ = file.WriteString(line + "\n")
		}
		rwMutex.RUnlock()
		_, _ = file.WriteString("\n\n")
		time.Sleep(500 * time.Millisecond)
	}
	exit <- 1
}

func Monitor2(garden [][]bool, rwMutex *sync.RWMutex, exit chan int) {

	for i := 0; i < MaxActions; i++ {
		rwMutex.RLock()
		fmt.Println("Printing plants' status")
		for i := 0; i < len(garden); i++ {
			var line string
			for j := 0; j < len(garden[0]); j++ {
				line += boolToString(garden[i][j]) + " "
			}
			fmt.Println(line)
		}
		rwMutex.RUnlock()
		fmt.Println()
		fmt.Println()
		time.Sleep(500 * time.Millisecond)
	}
	exit <- 1
}
