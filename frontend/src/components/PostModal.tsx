import { useCallback, useState } from "react"
import ErrorModal from "./ErrorModal"
import { useMutation } from "@tanstack/react-query"
import { createPost } from "@/utils/api"
import { PostInputs } from "@/features/home/utils/types"
import { postLimit } from "@/utils/constants"
import { TextareaAutosize } from "@mui/material"
import { useForm, useWatch, SubmitHandler } from "react-hook-form"
import LoadingIndicator from "./LoadingIndicator"
import avatar from "@/assets/profile_picture.jpg"
import PopUpModal from "./PopUpModal/PopUpModal"


interface PostModalProps {
    onClose: () => void
}


const PostModal = ({ onClose }: PostModalProps) => {

    const [showError, setShowError] = useState(false)
    const { control, handleSubmit, formState: { isValid }, register, reset } = useForm<PostInputs>({
        mode: "onChange",
        defaultValues: {
            content: ""
        }
    })
    const subscribeContent = useWatch({ control, name: 'content' })

    const calculateIndicatorColor = useCallback(() => {
        if (subscribeContent.length < postLimit - 20) {
            return "info"
        }
        else if (subscribeContent.length < postLimit) {
            return "warning"
        }
        return "error"
    }, [subscribeContent])

    const { mutate: mutatePost } = useMutation({
        mutationFn: createPost,
        mutationKey: ["post", "post"],
        onSuccess: () => {
            reset()
        },
        onError: (e) => {
            setShowError(true)
            console.log(e)
        }
    })

    const onSubmit: SubmitHandler<PostInputs> = ({ content }, e) => {
        e?.preventDefault();
        mutatePost(content)
    }

    const onErrorClose = () => {
        setShowError(false)
        reset()
    }

    return (
        <>
            <PopUpModal onClose={onClose} width={600} showLogo={false}>
                <form onSubmit={handleSubmit(onSubmit)} className="w-full h-full px-1">
                    <div className="w-full flex border-b-[1px] border-b-y-gray-400">
                        <img src={avatar} className="rounded-full w-[40px] h-[40px]" width={40} height={40}></img>
                        <div className="flex flex-col w-full pt-2">
                            <TextareaAutosize className="resize-none pb-3 bg-black outline-none ml-3 text-xl placeholder-y-gray-300" placeholder="What is happening?!" maxRows={19} minRows={4}{...register('content', {
                                required: true,
                                minLength: 1,
                                maxLength: postLimit
                            })} />
                        </div>
                    </div >
                    <div className="w-full flex justify-end items-center mt-2">
                        <span className="mr-2 text-sm" style={{ color: postLimit - subscribeContent.length <= 0 ? "#f44336" : "white", visibility: postLimit - subscribeContent.length <= 20 ? "visible" : "hidden" }}>{postLimit - subscribeContent.length}</span>
                        <LoadingIndicator variant="determinate" value={Math.min(subscribeContent.length / postLimit * 100, 100)} size={25} color={calculateIndicatorColor()} />
                        <input type="submit" className="ml-3 bg-y-accent-blue rounded-full px-5 py-[6px] font-bold transition-colors hover:bg-opacity-85 hover:cursor-pointer disabled:opacity-50" disabled={!isValid} value={"Post"}></input>
                    </div>
                </form>

            </PopUpModal>
            {showError && <ErrorModal onClose={onErrorClose} />}
        </>
    )
}

export default PostModal