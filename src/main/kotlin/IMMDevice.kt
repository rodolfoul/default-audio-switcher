import com.sun.jna.Pointer
import com.sun.jna.ptr.PointerByReference

class IMMDevice(pointer: Pointer) : ComUnknown(pointer) {
	fun GetId(): String {
		val result = PointerByReference()
		comCall(5, arrayOf(result))
		return result.value.getWideString(0)
	}
}