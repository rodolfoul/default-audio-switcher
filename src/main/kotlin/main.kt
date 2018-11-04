import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.Ole32


val acerX34 = "{0.0.0.00000000}.{82173b42-bf61-4af9-9f3e-34e0b4b34d11}"
val astroA50 = "{0.0.0.00000000}.{a2a72342-4f5c-45bc-a54d-b7a44d555834}"

fun main(args: Array<String>) {
	val ole32 = Ole32.INSTANCE

	COMUtils.checkRC(ole32.CoInitialize(null))

	try {
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

	} finally {
		ole32.CoUninitialize()
	}
}