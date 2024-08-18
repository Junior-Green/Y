import { UserProfile } from "@/utils/types"
import { Tooltip, TooltipProps } from "@mui/material"
import ProfileAvatar from "./ProfileAvatar"
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query"
import { followUser, getUserFollowed, getUserFollowers, unfollowUser } from "@/utils/api"
import { useUser } from "@/hooks/hooks"
import { RiVerifiedBadgeFill } from "react-icons/ri"
import { Link } from "react-router-dom"
import Paragraph from "./Paragraph"
import { abbreviateNumber } from "@/utils/helpers"

type UserProfileTooltipProps = Omit<TooltipProps, 'children' | 'title'> & {
    children: React.ReactElement<any, any>,
    user: UserProfile,
}

const UserProfileTooltip = ({ children, user, ...rest }: UserProfileTooltipProps) => (
    <Tooltip
        {...rest}
        title={<UserProfileModal user={user} />}
        slotProps={{
            popper: {
                modifiers: [
                    {
                        name: 'offset',
                        options: {
                            offset: [0, -10],
                        },
                    },
                ],
            },
        }}
        componentsProps={{
            tooltip: {
                sx: {
                    width: '300px',
                    height: 'min-content',
                    bgcolor: 'black',
                    borderRadius: "20px",
                    boxShadow: "0px 0px 15px -7px white"
                },
            },
        }}
    >
        {children}
    </Tooltip>
)

const UserProfileModal = ({ user }: { user: UserProfile }) => {
    const { user: authenticated, isSuccess } = useUser()
    const queryClient = useQueryClient()
    const { data: usersFollowed } = useQuery({
        queryKey: [authenticated!.id, 'following'],
        queryFn: getUserFollowed,
        staleTime: Infinity,
        enabled: isSuccess,
        placeholderData: []
    })
    const { mutate: followUserFn } = useMutation({
        mutationKey: [user.id, 'follow'],
        mutationFn: followUser,
        onMutate: async () => {
            await queryClient.cancelQueries({ queryKey: [authenticated!.id, 'following'] })

            const previousTodos = queryClient.getQueryData<string[]>([authenticated!.id, 'following'])

            queryClient.setQueryData<string[]>([authenticated!.id, 'following'], (old) => [...old!, user.id])

            return { previousTodos }
        },
        // If the mutation fails,
        // use the context returned from onMutate to roll back
        onError: (_err, _newTodo, context) => {
            queryClient.setQueryData([authenticated!.id, 'following'], context!.previousTodos)
        },
        // Always refetch after error or success:
        onSettled: () => {
            queryClient.invalidateQueries({ queryKey: [authenticated!.id, 'following'] })
        },
    })
    const { mutate: unfollowUserFn } = useMutation({
        mutationKey: [user.id, 'unfollow'],
        mutationFn: unfollowUser,
        onMutate: async () => {
            await queryClient.cancelQueries({ queryKey: [authenticated!.id, 'following'] })

            const previousTodos = queryClient.getQueryData<string[]>([authenticated!.id, 'following'])

            queryClient.setQueryData<string[]>([authenticated!.id, 'following'], (old) => old!.filter((val) => user.id !== val))

            return { previousTodos }
        },
        // If the mutation fails,
        // use the context returned from onMutate to roll back
        onError: (_err, _newTodo, context) => {
            queryClient.setQueryData([authenticated!.id, 'following'], context!.previousTodos)
        },
        // Always refetch after error or success:
        onSettled: () => {
            queryClient.invalidateQueries({ queryKey: [authenticated!.id, 'following'] })
        },
    })

    const handleFollow = () => {
        followUserFn(user.id)
    }

    const handleUnfollow = () => {
        unfollowUserFn(user.id)
    }

    return (
        <div className="w-full h-full flex flex-col p-3">
            <div className="flex justify-between w-full">
                <ProfileAvatar name={user.displayName} width={60} height={60}></ProfileAvatar>
                {usersFollowed!.includes(user.id) && user.id !== authenticated?.id && <button className="border-red-600 border-[1px] rounded-full hover:bg-red-600/15 transition-colors py-[6px] px-4 h-min" onClick={handleUnfollow}>Unfollow</button>}
                {!usersFollowed!.includes(user.id) && user.id !== authenticated?.id && <button className="bg-white font-semibold text-base text-black rounded-full hover:bg-white/90 transition-colors py-[6px] px-4 h-min" onClick={handleFollow}>Follow</button>}
            </div>
            <div className="flex items-center mt-2">
                <Link className="font-semibold text-lg hover:underline decoration-white decoration-2 hover:cursor-pointer" to={`/${user.username}`}>{user.displayName}</Link>
                {user.isVerified && <RiVerifiedBadgeFill color="#1DA1F2" size={15} className="ml-1" />}
            </div>
            <Link className="text-base leading-tight text-y-gray-300 w-min whitespace-nowrap text-nowrap overflow-clip" to={`/${user.username}`}>{`@${user.username}`}</Link>
            <div className="mt-3 text-sm text-white font-normal leading-snug">
                <Paragraph >{user.bio ?? ""}</Paragraph>
            </div>
            <div className="flex mt-3">
                <Link className="text-white font-medium text-sm hover:underline mr-4" to={`/${user.username}/following`}>{abbreviateNumber(user.followingCount ?? 0)} <span className="font-normal text-y-gray-300">Following</span></Link>
                <Link className="text-white font-medium text-sm hover:underline" to={`/${user.username}/followers`}>{abbreviateNumber(user.followerCount ?? 0)} <span className="font-normal text-y-gray-300">Followers</span></Link>
            </div>
        </div>
    )
}

export default UserProfileTooltip