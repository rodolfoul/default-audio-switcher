import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.PointerByReference

class IMMDeviceCollection(pointer: Pointer) : ComUnknown(pointer) {

	fun Item(index: Long): IMMDevice {
		val result = PointerByReference()

		comCall(4, arrayOf(WinDef.UINT(index), result))

		return IMMDevice(result.value)
	}

	fun GetCount(): Long {
		val result = WinDef.UINTByReference()
		comCall(3, arrayOf(result))

		return result.value.toLong()
	}
}
