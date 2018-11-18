import com.changeDefaultAudioPlayback
import com.listAudioSinks
import com.playBeep

fun main(args: Array<String>) {
	if (args.isNotEmpty() && args[0] == "-l") {
		listAudioSinks()

	} else {
		changeDefaultAudioPlayback()
	}

	playBeep()
}