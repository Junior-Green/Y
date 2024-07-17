import PopUpModal from "./PopUpModal/PopUpModal"

interface LogoutModalProps {
    onClose: () => void
}

const LogoutModal = ({ onClose }: LogoutModalProps) =>
(
    <PopUpModal width={320} height={356} onClose={onClose} showLogo={false} showCloseButton={false} >
        <div className="w-full h-full flex flex-col">
            <h1 className="w-full text-white font-bold text-xl">Error</h1>
            <p className="text-y-gray-300 leading-tight mt-2">Oops, something went wrong. Please try again later. </p>
            <button onClick={onClose} className="mt-auto bg-white text-black p-2 rounded-full hover:bg-opacity-85 transition-colors font-semibold">OK</button>
        </div>
    </PopUpModal>
)

export default LogoutModal