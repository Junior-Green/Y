import { QueryFunctionContext } from "@tanstack/react-query"
import { DELETE, GET, POST, PUT } from "./fetch"
import { CreateReplyParams, Like, LoginCredentials, NewUserRequest, Page, Post, UpdateUserRequest, User, UserProfile, UserProfileRequest } from "./types"

export const getTimeNow = async (): Promise<Date> => {
    const response = await fetch('https://worldtimeapi.org/api/timezone/America/Toronto')
    const time = await response.json()

    const date = new Date(time.datetime.replace(/[+-]\d\d:\d\d$/, ''))
    return date
}

export const getPaginatedPostsByLikes = async ({ pageParam }: QueryFunctionContext): Promise<Page<Post>> => {
    return GET(`/api/posts/popular?page=${pageParam}`).then((res) => res.data)
}

export const getPaginatedFollowersPosts = async ({ pageParam }: QueryFunctionContext): Promise<Page<Post>> => {
    return GET(`/api/posts/followers?page=${pageParam}`).then(res => res.data)
}

export const getPostById = async (postId: string): Promise<Post> => {
    return GET(`/api/posts/${postId}`).then(res => res.data)
}

export const getPostsFromAuthenticatedUser = async (): Promise<Post[]> => {
    return GET(`/api/posts/me`).then(res => res.data)
}

export const getPostsFromHashtag = async (hashtag: string): Promise<Post[]> => {
    return GET(`/api/posts/hashtag/${hashtag}`).then(res => res.data)
}

export const getAuthenticatedUser = async (): Promise<User> => {
    return GET(`/api/users/me`).then(res => res.data)
}

export const getUserFollowers = async ({ queryKey }: QueryFunctionContext): Promise<String[]> => {
    const [id, _] = queryKey
    return GET(`/api/users/${id}/followers`).then(res => res.data)
}

export const getUserFollowed = async ({ queryKey }: QueryFunctionContext): Promise<String[]> => {
    const [id, _] = queryKey
    return GET(`/api/users/${id}/following`).then(res => res.data)
}

export const getUserProfileFromContext = async ({ queryKey }: QueryFunctionContext): Promise<UserProfile> => {
    const [_, identifier, value] = queryKey
    return GET(`/api/users?${identifier}=${value}`).then(res => res.data)
}

export const getUserProfile = async ({ identifier, value }: UserProfileRequest): Promise<UserProfile> => {
    return GET(`/api/users?${identifier}=${value}`).then(res => res.data)
}

export const getAllLikesByUser = async (userId: string): Promise<Like[]> => {
    return GET(`/api/users/likes/${userId}`).then(res => res.data)
}

// ------------------------------MUTATING-------------------------------

export const login = async (cred: LoginCredentials): Promise<void> => {
    await POST("/api/auth/login", cred);
}

export const logout = async (): Promise<void> => {
    await POST("/api/auth/logout", {});
}

export const createPost = async (content: string): Promise<Post> => {
    return POST("/api/posts", content).then(res => res.data)
}

export const createQoutedPost = async (content: string, qoutedPostId: string): Promise<Post> => {
    return POST(`/api/posts?qoutedId=${qoutedPostId}`, content).then(res => res.data)

}

export const likePost = async (postId: string): Promise<string> => {
    return POST(`/api/posts/like/${postId}`, {}).then(res => res.data)
}

export const createReply = async ({ content, parentId }: CreateReplyParams): Promise<Post> => {
    return POST(`/api/posts/${parentId}`, content).then(res => res.data)
}

export const deletePost = async (postId: string): Promise<string> => {
    return DELETE(`/api/posts/${postId}`).then(res => res.data)
}

export const unlikePost = async (postId: string): Promise<string> => {
    return DELETE(`/api/posts/like/${postId}`).then(res => res.data)
}

export const createNewUser = async (userData: NewUserRequest): Promise<User> => {
    return POST("/api/users/register", userData).then(res => res.data)
}

export const followUser = async (userId: string): Promise<string> => {
    return POST(`/api/users/follow/${userId}`, {}).then(res => res.data)
}

export const blockUser = async (userId: string): Promise<string> => {
    return POST(`/api/users/block/${userId}`, {}).then(res => res.data)
}

export const updateUser = async (userData: UpdateUserRequest): Promise<User> => {
    return PUT("/api/users/me", userData).then(res => res.data)
}

export const unfollowUser = async (userId: string): Promise<string> => {
    return DELETE(`/api/users/unfollow/${userId}`).then(res => res.data)
}

export const unblockUser = async (userId: string): Promise<string> => {
    return DELETE(`/api/users/unblock/${userId}`).then(res => res.data)
}

export const deleteUser = async (): Promise<void> => {
    await DELETE("/api/users/me");
}