import { DELETE, GET, POST } from "./fetch"
import { Page, Post, User } from "./types"

export  const getPaginatedPosts = async (pageNumber: number): Promise<Page<Post>> => {
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

export const getAuthenticatedUser = async (): Promise<User> => {
    const data: User = await GET(`/api/users/me`);
    return data;
}