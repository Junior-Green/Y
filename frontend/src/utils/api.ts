import { DELETE, GET, POST, PUT } from "./fetch"
import { Like, LoginCredentials, NewUserRequest, Page, Post, UpdateUserRequest, User, UserProfile } from "./types"

export const getTimeNow = async (): Promise<Date> => {
    const response = await fetch('https://worldtimeapi.org/api/timezone/America/Toronto')
    const time = await response.json()

    const date = new Date(time.datetime.replace(/[+-]\d\d:\d\d$/, ''))
    return date
}

export const getPaginatedPosts = async (pageNumber: number): Promise<Page<Post>> => {
    const data: Page<Post> = await GET(`/api/posts?page=${pageNumber}`)
    return data
}

export const getPaginatedFollowersPosts = async (pageNumber: number): Promise<Page<Post>> => {
    const data: Page<Post> = await GET(`/api/posts/followers?page=${pageNumber}`)
    return data
}

export const getPostById = async (postId: string): Promise<Post> => {
    const data: Post = await GET(`/api/posts/${postId}`)
    return data;
}

export const getPostsFromAuthenticatedUser = async (): Promise<Post[]> => {
    const data: Post[] = await GET(`/api/posts/me`)
    return data;
}

export const getPostsFromHashtag = async (hashtag: string): Promise<Post[]> => {
    const data: Post[] = await GET(`/api/posts/hashtag/${hashtag}`)
    return data;
}

export const getAuthenticatedUser = async (): Promise<User> => {
    const data: User = await GET(`/api/users/me`);
    return data;
}

export const getUserProfile = async (identifier: "id" | "email" | "phone" | "username", value: string): Promise<UserProfile> => {
    const data: UserProfile = await GET(`/api/users?${identifier}=${value}`);
    return data;
}

export const getAllLikesByUser = async (userId: string): Promise<Like[]> => {
    const data: Like[] = await GET(`/api/users/likes/${userId}`);
    return data;
}

// ------------------------------MUTATING-------------------------------

export const login = async (cred: LoginCredentials): Promise<void> => {
    await POST("/api/auth/login", cred);
}

export const logout = async (): Promise<void> => {
    await POST("/api/auth/logout", {});
}

export const createPost = async (content: string): Promise<Post> => {
    const data: Post = await POST("/api/posts", content);
    return data
}

export const createQoutedPost = async (content: string, qoutedPostId: string): Promise<Post> => {
    const data: Post = await POST(`/api/posts?qoutedId=${qoutedPostId}`, content);
    return data
}

export const likePost = async (postId: string): Promise<string> => {
    const data: string = await POST(`/api/posts/like/${postId}`, {});
    return data
}

export const createReply = async (parentPostId: string, content: string): Promise<Post> => {
    const data: Post = await POST(`/api/posts/${parentPostId}`, content);
    return data
}

export const deletePost = async (postId: string): Promise<string> => {
    const data: string = await DELETE(`/api/posts/${postId}`);
    return data
}

export const unlikePost = async (postId: string): Promise<string> => {
    const data: string = await DELETE(`/api/posts/like/${postId}`);
    return data
}


export const createNewUser = async (userData: NewUserRequest): Promise<User> => {
    const data: User = await POST("/api/users/register", userData);
    return data;
}

export const followUser = async (userId: string): Promise<string> => {
    const data: string = await POST(`/api/users/follow/${userId}`, {});
    return data;
}

export const blockUser = async (userId: string): Promise<string> => {
    const data: string = await POST(`/api/users/block/${userId}`, {});
    return data;
}

export const updateUser = async (userData: UpdateUserRequest): Promise<User> => {
    const data: User = await PUT("/api/users/me", userData);
    return data;
}

export const unfollowUser = async (userId: string): Promise<string> => {
    const data: string = await DELETE(`/api/users/unfollow/${userId}`);
    return data;
}

export const unblockUser = async (userId: string): Promise<string> => {
    const data: string = await DELETE(`/api/users/unblock/${userId}`);
    return data;
}

export const deleteUser = async (): Promise<void> => {
    await DELETE("/api/users/me");
}