package com

import com.imm.IMMDeviceEnumerator
import com.imm.IPolicyConfigVista
import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.Ole32
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

fun changeDefaultAudioPlayback(name1: String, name2: String) {
	comEnv {
		IMMDeviceEnumerator().use { immEnumerator ->
			val endpoint1 = immEnumerator.resolveAudioEndpoint(name1)
			val endpoint2 = immEnumerator.resolveAudioEndpoint(name2)
			println("Resolving between '${endpoint1.name}' and '${endpoint2.name}'")

			val currentDefaultId = immEnumerator.GetDefaultAudioEndpoint().GetId()

			val newDefaultId = if (currentDefaultId == endpoint1.id) {
				println("Setting default audio to ${endpoint2.name}")
				endpoint2.id
			} else {
				println("Setting default audio to: ${endpoint1.name}")
				endpoint1.id
			}

			IPolicyConfigVista().use {
				it.SetDefaultEndpoint(newDefaultId)
			}
		}
	}
}

fun comEnv(runnable: () -> Unit) {
	val ole32 = Ole32.INSTANCE
	COMUtils.checkRC(ole32.CoInitialize(null))
	try {
		runnable()
	} finally {
		ole32.CoUninitialize()
	}
}

fun playBeep() {
	val audioStream = AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/beeping.wav"))
	val format = audioStream.format
	val info = DataLine.Info(Clip::class.java, format)
	val clip = AudioSystem.getLine(info) as Clip
	val clipEndedLatch = CountDownLatch(1)
	clip.addLineListener {
		if (it.type == LineEvent.Type.STOP) {
			clipEndedLatch.countDown()
		}
	}
	clip.open(audioStream)
	clip.start()
	clipEndedLatch.await()
}