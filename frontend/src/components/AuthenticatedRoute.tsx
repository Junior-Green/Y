import { useUser } from "@/hooks/hooks";
import React from "react";
import { redirect } from "react-router-dom";

interface AuthenticatedRouteProps {
    children: React.ReactNode
}

export default function AuthenticatedRoute({ children }: AuthenticatedRouteProps) {
    const { user, error, isLoading } = useUser()

    if (error || !user) {
        redirect("/")
    }

    return (
        <>
            {!isLoading && children}
        </>
    )
}