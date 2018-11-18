package com

import com.imm.IMMDeviceEnumerator
import com.imm.IPolicyConfigVista
import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.COM.util.Factory
import com.sun.jna.platform.win32.Ole32
import com.wmp.IWMPPlayer3
import com.wmp.WindowsMediaPlayer
import java.util.concurrent.CountDownLatch
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine
import javax.sound.sampled.LineEvent

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
		val audioEndpoints = IMMDeviceEnumerator().use {
			it.getAudioEndpoints()
		}

		var acerX34Id = ""
		var astroA50Id = ""

		for (audioEndpoint in audioEndpoints) {
			if (audioEndpoint.second.contains("acer x34", true)) {
				acerX34Id = audioEndpoint.first
			} else if (audioEndpoint.second.contains("astro", true)) {
				astroA50Id = audioEndpoint.first
			}
		}

		val currentDefault = IMMDeviceEnumerator().use {
			it.GetDefaultAudioEndpoint().GetId()
		}

		val newDefault = if (acerX34Id == currentDefault) {
			println("Setting default to Astro A50")
			astroA50Id
		} else {
			println("Setting default to Acer x34")
			acerX34Id
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
	javaxPlaySound()
}

class Dummy

private fun javaxPlaySound() {
	val audioStream = AudioSystem.getAudioInputStream(Dummy::class.java.getResource("/beeping.wav"))
	val format = audioStream.format
	val info = DataLine.Info(Clip::class.java, format)
	val clip = AudioSystem.getLine(info) as Clip

	val clipEndedLatch = CountDownLatch(1)

	clip.addLineListener {
		if (it.type == LineEvent.Type.STOP) {
			println("Beep stream stopped")
			clipEndedLatch.countDown()
		}
	}

	clip.open(audioStream)
	clip.start()
	clipEndedLatch.await()
}


private fun wmpPlaySound() {
	val factory = Factory()
	val windowsMediaPlayer = factory.createObject(WindowsMediaPlayer::class.java)

	val iwmpPlayer3 = windowsMediaPlayer.queryInterface(IWMPPlayer3::class.java)
	iwmpPlayer3.setEnabled(true)
	iwmpPlayer3.setURL("beeping.wav")
	println(iwmpPlayer3.getVersionInfo())


	val controls = iwmpPlayer3.getControls()
	controls.play()
}