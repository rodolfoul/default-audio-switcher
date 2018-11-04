import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.ObjBase
import com.sun.jna.platform.win32.Ole32
import com.sun.jna.platform.win32.Ole32Util
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.PointerByReference

//https://hackage.haskell.org/package/bindings-portaudio-0.1/src/portaudio/src/hostapi/wasapi/mingw-include/mmdeviceapi.h
class IMMDeviceEnumerator : ComUnknown() {

	init {
		val MMDeviceEnumeratorGuid = Ole32Util.getGUIDFromString("{BCDE0395-E52F-467C-8E3D-C4579291692E}")
		val IMMDeviceEnumeratorGuid = Ole32Util.getGUIDFromString("{A95664D2-9614-4F35-A746-DE8DB63617E6}")
		val enumeratorPointer = PointerByReference()
		COMUtils.checkRC(Ole32.INSTANCE.CoCreateInstance(MMDeviceEnumeratorGuid,
				null,
				ObjBase.CLSCTX_ALL,
				IMMDeviceEnumeratorGuid,
				enumeratorPointer))

		pointer = enumeratorPointer.value
	}

	/**
	 * HRESULT EnumAudioEndpoints(
	 * EDataFlow           dataFlow,
	 * DWORD               dwStateMask,
	 * IMMDeviceCollection **ppDevices
	);
	 */
	fun EnumAudioEndpoints(): List<String> {
		val result = PointerByReference()

		comCall(3, arrayOf(EDataFlow.eRender.ordinal, WinDef.DWORD(DeviceState.DEVICE_STATE_ACTIVE.value)))

		val immDeviceCollection = IMMDeviceCollection(result.value)

		val list = mutableListOf<String>()
		for (i in 0 until immDeviceCollection.GetCount()) {
			val item = immDeviceCollection.Item(i)
			list.add(item.GetId())
		}

		return list
	}

	fun GetDefaultAudioEndpoint(): IMMDevice {
		val result = PointerByReference()

		comCall(4,
				arrayOf(EDataFlow.eRender.ordinal,
						WinDef.DWORD(DeviceState.DEVICE_STATE_ACTIVE.value),
						result))

		return IMMDevice(result.value)
	}
}