import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.Ole32
import com.IMMDeviceEnumerator
import com.IPolicyConfigVista
import javax.sound.sampled.AudioSystem


val acerX34 = "{0.0.0.00000000}.{13815a34-abaa-4b61-bbc7-38ca2932bf32}"
val astroA50 = "{0.0.0.00000000}.{a2a72342-4f5c-45bc-a54d-b7a44d555834}"

fun main(args: Array<String>) {
	if (args.isNotEmpty() && args[0] == "-l") {
		listAudioSinks()

	} else {
		changeDefaultAudioPlayback()
	}

//	playBeep()
	println("Ending main thread...")
}

fun listAudioSinks() {
	comEnv {
		IMMDeviceEnumerator().use {
			val audioEndpoints = it.getAudioEndpoints()
			println(audioEndpoints.joinToString("\n"))
		}
	}
}

fun changeDefaultAudioPlayback() {
	comEnv {
		val currentDefault = IMMDeviceEnumerator().use {
			it.GetDefaultAudioEndpoint().GetId()
		}

		val newDefault = if (acerX34 == currentDefault) {
			println("Setting default to Astro A50")
			astroA50
		} else {
			println("Setting default to Acer x34")
			acerX34
		}

		IPolicyConfigVista().use {
			it.SetDefaultEndpoint(newDefault)
		}
	}
}

fun comEnv(f: () -> Unit) {
	val ole32 = Ole32.INSTANCE
	COMUtils.checkRC(ole32.CoInitialize(null))
	try {
		f()
	} finally {
		ole32.CoUninitialize()
	}
}

fun playBeep() {
	val beepingResource = String::class.java.getResource("/beeping.wav")
	val clip = AudioSystem.getClip()

	beepingResource.openStream().use {
		AudioSystem.getAudioInputStream(it).use { audioStream ->
			clip.open(audioStream)
//			clip.loop(1)
			clip.start()
			audioStream.frameLength
		}
	}

//	JFXPanel()
//	PlatformImpl.startup {  }
//	val toolkit = Toolkit.getToolkit()
//	toolkit.init()
//	PlatformImpl.add*/Listener(
//			object: PlatformImpl.FinishListener {
//				override fun exitCalled() {
//				}
//
//			})
//	val beepMedia = Media(beepingResource.toURI().toString())
//	val player = MediaPlayer(beepMedia)
//
//	player.onReady = Runnable {
//		player.play()
//	}
//
//	player.onEndOfMedia = Runnable {
//		println("on end of media")
//		Platform.exit()
//		System.exit(0)
//	}

//	player.play()

//	val vv = beepMedia.duration.toMillis().roundToLong()
//	Timer().schedule(object : TimerTask() {
//		override fun run() {
//			println("End time")
//		}
//	}, vv)
}