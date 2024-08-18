import { Post } from "@/utils/types"
import ProfileAvatar from "./ProfileAvatar"
import { RiVerifiedBadgeFill } from "react-icons/ri";
import { useDate } from "@/hooks/hooks";
import { FaRetweet } from "react-icons/fa6";
import { FaRegComment } from "react-icons/fa";
import { FaRegHeart } from "react-icons/fa";
import { FaRegBookmark } from "react-icons/fa6";
import { abbreviateNumber, dateDifferenceToString, formatDateString } from "@/utils/helpers";
import { Tooltip } from "@mui/material";
import { useState } from "react";
import UserProfileTooltip from "./UserProfileTooltip";
import { Link } from "react-router-dom";
import Paragraph from "./Paragraph";

interface PostDisplayProps {
    post: Post
}

const PostDisplay = ({ post }: PostDisplayProps) => {
    const [isQouted, setIsQouted] = useState(false);
    const [isLiked, setIsLiked] = useState(false)
    const { date } = useDate()

    return (
        <div className="w-full flex border-b-[1px] border-b-y-gray-400 pt-3 pb-2 px-5 hover:cursor-pointer transition-colors hover:bg-gray-600/10">
            <ProfileAvatar name={post.author.displayName} width={40} height={40} />
            <div className="w-full flex flex-col ml-2">
                <div className="flex items-center">
                    <UserProfileTooltip user={post.author} enterDelay={1000}>
                        <Link className="font-bold w-min whitespace-nowrap text-nowrap overflow-clip hover:underline decoration-white decoration-2 hover:cursor-pointer" to={`/${post.author.username}`}>{post.author.displayName}</Link>
                    </UserProfileTooltip>
                    {post.author.isVerified && <RiVerifiedBadgeFill color="#1DA1F2" size={15} className="ml-1" />}
                    <UserProfileTooltip user={post.author} enterDelay={1000}>
                        <Link className="ml-1 text-y-gray-300 font-semibold w-min whitespace-nowrap text-nowrap overflow-clip" to={`/${post.author.username}`}>{`@${post.author.username}`}</Link>
                    </UserProfileTooltip>
                    <span className="mx-1 text-y-gray-300">·</span>
                    <Tooltip title={formatDateString(post.createdAt)} enterDelay={750} slotProps={{
                        popper: {
                            modifiers: [
                                {
                                    name: 'offset',
                                    options: {
                                        offset: [0, -15],
                                    },
                                },
                            ],
                        },
                    }}>
                        <span className=" text-y-gray-300 font-semibold w-min whitespace-nowrap text-nowrap hover:underline decoration-y-gray-300 hover:cursor-pointer">{dateDifferenceToString(post.createdAt, date!)}</span>
                    </Tooltip>
                </div>
                <Paragraph>{post.content}</Paragraph>
                <div className="flex w-full justify-between mt-3">
                    <div className="flex justify-between w-3/5">
                        <button className="flex items-center text-y-gray-300">
                            <FaRegComment size={17} />
                            <span className="mb-[2px] ml-1 ">
                                {abbreviateNumber(post.replyCount ?? 0)}
                            </span>
                        </button>
                        <button className="flex items-center text-y-gray-300">
                            <FaRetweet size={20} />
                            <span className="mb-[2px] ml-1 ">
                                {abbreviateNumber(post.quoteCount ?? 0)}
                            </span>
                        </button>
                        <button className="flex items-center text-y-gray-300">
                            <FaRegHeart size={17} />
                            <span className="mb-[2px] ml-1 ">
                                {abbreviateNumber(post.likesCount ?? 0)}
                            </span>
                        </button>
                    </div>
                    <button>
                        <FaRegBookmark className="text-y-gray-300" />
                    </button>
                </div>
            </div>
        </div >
    )
}

export default PostDisplay