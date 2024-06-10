import Modal from 'react-modal';
import "./PopUpModal.css"
import { useRef } from 'react';
import { FaXmark } from 'react-icons/fa6';
import Logo from '@/components/Logo';

interface PopUpModalProps {
    children?: React.ReactNode
    isOpen: boolean,
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>
}

const PopUpModal = ({ isOpen, setIsOpen, children }: PopUpModalProps) => {

    useRef

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={() => { setIsOpen(false) }}
            overlayClassName="y-overlay"
            appElement={document.getElementById('root') || undefined}
            className="y-modal">
            <div className='w-full h-full bg-black rounded-3xl flex flex-col p-3 relative'>
                <div className='logo-container'>
                    <Logo widthValue={30} widthUnit={'px'} hexColor={'#ffffff'}>
                    </Logo>
                </div>
                <div className='w-full'>
                    <button className='rounded-full p-2 hover:bg-gray-500 transition-colors hover:bg-opacity-30'
                        onClick={() => setIsOpen(false)}>
                        <FaXmark color='white' size="20px"></FaXmark>
                    </button>
                </div>
                <div className='w-full h-full px-[12%] py-4'>
                    {children}
                </div>

            </div>

        </Modal>
    )
}


export default PopUpModal;