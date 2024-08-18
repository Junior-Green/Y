import { useNavigate } from "react-router-dom"
import PopUpModal from "./PopUpModal/PopUpModal"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { logout } from "@/utils/api"
import { useState } from "react"
import ErrorModal from "./ErrorModal"

interface LogoutModalProps {
    onClose: () => void
}

const LogoutModal = ({ onClose }: LogoutModalProps) => {
    const [showError, setShowError] = useState(false)
    const client = useQueryClient()
    const navigate = useNavigate()
    const { mutate } = useMutation({
        mutationFn: logout,
        onSuccess: () => {
            client.removeQueries({
                queryKey: ['user', 'authenticated'],
                exact: false
            })
            navigate("/")
        },
        onError: () => {
            setShowError(true)
        }
    })

    const errorOnClose = () => {
        setShowError(false)
        onClose()
    }

    const onLogout = () => {
        mutate()
    }

    return (
        <>
            <PopUpModal width={320} height={356} onClose={onClose} showLogo={true} showCloseButton={false}>
                <div className="w-full h-full flex flex-col p-6">
                    <h1 className="w-full text-white font-bold text-xl">Log out of Y?</h1>
                    <p className="text-y-gray-300 leading-tight mt-2">You can always log back in at any time. </p>
                    <button onClick={onLogout} className="mt-auto bg-white text-black p-2 rounded-full hover:bg-opacity-85 transition-colors font-semibold">Log out</button>
                    <button onClick={onClose} className="mt-3 border-white border-[1px] bg-transparent text-white p-2 rounded-full hover:bg-opacity-25 hover:bg-gray-600 transition-colors font-semibold">Cancel</button>
                </div>
            </PopUpModal>
            {showError && <ErrorModal onClose={errorOnClose}></ErrorModal>}
        </>
    )
}

export default LogoutModal