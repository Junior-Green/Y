import Modal from 'react-modal';
import "./PopUpModal.css"
import { FaXmark } from 'react-icons/fa6';
import Logo from '@/components/Logo';

interface PopUpModalProps {
    children?: React.ReactNode
    onClose: (event?: React.MouseEvent<Element, MouseEvent> | React.KeyboardEvent<Element>) => void
    showCloseButton?: boolean,
    showLogo?: boolean
    width: number,
    height: number
}

const PopUpModal = ({ onClose, children, showCloseButton = true, showLogo = true, width, height }: PopUpModalProps) =>
(
    <Modal
        isOpen={true}
        onRequestClose={onClose}
        overlayClassName="y-overlay"
        shouldCloseOnEsc={false}
        shouldCloseOnOverlayClick={false}
        appElement={document.getElementById('root') || undefined}
        className="y-modal">
        <div className={`bg-black rounded-3xl flex flex-col p-3 relative`} style={{ width: `${width}px`, height: `${height}px` }}>
            {showLogo &&
                <div className='logo-container'>
                    <Logo widthValue={30} widthUnit={'px'} hexColor={'#ffffff'}>
                    </Logo>
                </div>
            }
            {showCloseButton &&
                <div className='w-full'>
                    <button className='rounded-full p-2 hover:bg-gray-500 transition-colors hover:bg-opacity-30'
                        onClick={() => onClose()}>
                        <FaXmark color='white' size="20px"></FaXmark>
                    </button>
                </div>
            }
            <div className='w-full h-full px-6 pt-12 pb-5'>
                {children}
            </div>
        </div>
    </Modal>
)

export default PopUpModal;