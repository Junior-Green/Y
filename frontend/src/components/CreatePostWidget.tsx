import { TextareaAutosize } from '@mui/base/TextareaAutosize';
import { PostInputs } from "../features/home/utils/types";
import { SubmitHandler, useForm, useWatch } from "react-hook-form";
import { useMutation } from "@tanstack/react-query";
import { createPost, createReply } from "@/utils/api";
import { useCallback, useState } from "react";
import ErrorModal from "@/components/ErrorModal";
import LoadingIndicator from "@/components/LoadingIndicator";
import { postLimit } from "@/utils/constants";
import ProfileAvatar from "./ProfileAvatar";
import { useUser } from "@/hooks/hooks";

interface CreatePostWidgetProps {
    parentPostId?: string
}

const CreatePostWidget = ({ parentPostId }: CreatePostWidgetProps) => {
    const { user } = useUser();
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

    const { mutate: mutateReply } = useMutation({
        mutationFn: createReply,
        mutationKey: ["reply", "post"],
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
        if (parentPostId) {
            mutateReply({ content: content, parentId: parentPostId })
        }
        else {
            mutatePost(content)
        }
    }

    const onErrorClose = () => {
        setShowError(false)
        reset()
    }

    return (
        <>
            <div className="w-full flex border-b-[1px] border-b-y-gray-400 py-3 px-5">
                <ProfileAvatar name={user?.displayName ?? "Y"} width={40} height={40} />
                <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                    <div className="flex flex-col w-full pt-2">
                        <TextareaAutosize className="resize-none pb-3 bg-black outline-none ml-3 text-xl focus:border-b-[1px] border-b-y-gray-400 placeholder-y-gray-300" placeholder="What is happening?!" {...register('content', {
                            required: true,
                            minLength: 1,
                            maxLength: postLimit
                        })} />
                        <div className="w-full flex justify-end items-center mt-2">
                            <span className="mr-2 text-sm" style={{ color: postLimit - subscribeContent.length <= 0 ? "#f44336" : "white", visibility: postLimit - subscribeContent.length <= 20 ? "visible" : "hidden" }}>{postLimit - subscribeContent.length}</span>
                            <LoadingIndicator disableShrink={false} variant="determinate" value={Math.min(subscribeContent.length / postLimit * 100, 100)} size={25} color={calculateIndicatorColor()} />
                            <input type="submit" className="ml-3 bg-y-accent-blue rounded-full px-5 py-[6px] font-bold transition-colors hover:bg-opacity-85 hover:cursor-pointer disabled:opacity-50 disabled:cursor-default" disabled={!isValid} value={parentPostId ? "Reply" : "Post"}></input>
                        </div>
                    </div>
                </form >
            </div >
            {showError && <ErrorModal onClose={onErrorClose} />}
        </>
    )
}

export default CreatePostWidget