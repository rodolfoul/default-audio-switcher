import com.changeDefaultAudioPlayback
import com.listAudioSinks
import com.playBeep

fun main(args: Array<String>) {
	if (args.isEmpty()) {
		printHelp()
		return
	}

	if (args[0] == "-l") {
		listAudioSinks()

	} else if (args.size == 2) {
		changeDefaultAudioPlayback(args[0], args[1])
		playBeep()
	} else {
		printHelp()
		return
	}
}

private fun printHelp() {
	println("Please add endpoints for checking or -l for listing all endpoints")
}