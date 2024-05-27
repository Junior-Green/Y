import { store } from "@/stores/store"

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch

export type Page<T> = {
    data: Array<T> | null,
    nextPage: number | null,
    previosPage: number | null,
}


export type Post = {
    id: string,
    authorId: string,
    parentId: string | null,
    content: string,
    createdAt: Date,
    replyIds: string[],
    isQuotePost: boolean,
    likes: Like[]
}

export type Like = {
    likedByUserId: string,
    likedPostId: string,
    created: Date,
}

export type User = {
    id: string,
    username: string,
    firstName: string,
    middleName: string | null,
    lastName: string,
    email: string | null,
    birthday: Date,
    accountCreation: Date,
    phoneNumber: string | null,
    displayName: string,
    websiteUrl: string | null,
    location: string | null,
    isVerifided: boolean,
    gender: string | null,
    bio: string | null,
}

export type UserProfile = {
    id: string,
    displayName: string,
    location: string | null,
    websiteUrl: string | null,
    birthday: Date,
    verified: boolean,
    bio: string | null,
    accountCreation: Date,
    followersIds: string[],
    followingIds: string[]
}