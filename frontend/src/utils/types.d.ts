import { store } from "@/stores/store"

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch

export type Page<T> = {
    data: Array<T> | null,
    nextPage: number | null,
    previousPage: number | null,
}

export type Post = {
    id: string,
    author: UserProfile,
    parentId: string | null,
    content: string,
    createdAt: Date,
    isQuotePost: boolean,
    replyCount: number | null,
    quoteCount: number | null,
    likesCount: number | null,
    lastLikeDate: Date | null,
    firstLikeDate: Date | null
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
    bookmarks: Post[],
    phoneNumber: string | null,
    displayName: string,
    websiteUrl: string | null,
    location: string | null,
    isVerified: boolean,
    gender: string | null,
    bio: string | null,
}

export type UserProfile = {
    id: string,
    username: string
    displayName: string,
    location: string | null,
    websiteUrl: string | null,
    birthday: Date,
    isVerified: boolean,
    bio: string | null,
    accountCreation: Date,
    followerCount: number | null,
    followingCount: number | null,
    bookmarkCount: number | null,
    blockedCount: number | null,
}

export type UserProfileRequest = {
    identifier: "id" | "email" | "phone" | "username"
    value: string
}

export type NewUserRequest = {
    user: {
        username: string,
        email: string | null,
        phoneNumber: string | null,
        displayName: string,
        birthday: Date
    },
    password: string
}

export type UpdateUserRequest = {
    username?: string,
    firstName?: string,
    middleName?: string,
    lastName?: string,
    email?: string,
    birthday?: Date,
    phoneNumber?: string,
    displayName?: string,
    websiteUrl?: string,
    location?: string,
    gender?: string,
    bio?: string,
}

export type CreateReplyParams = {
    parentId: string,
    content: string
}

export type LoginCredentials = {
    username: string,
    password: string
}