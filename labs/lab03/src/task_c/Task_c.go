package main

func main() {
	randomInit()

	t := table{}

	for {
		go t.Provider()
		go t.SmokerWithTobacco()
		go t.SmokerWithPaper()
		go t.SmokerWithMatches()
	}
}
