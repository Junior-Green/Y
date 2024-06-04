import { useUser } from "@/hooks/hooks";
import React from "react";
import { redirect } from "react-router-dom";

interface AuthenticatedRouteProps {
    children: React.ReactNode
}

export default function AuthenticatedRoute({ children }: AuthenticatedRouteProps) {
    const { data, error, isLoading } = useUser()

    if (error || !data) {
        redirect("/")
    }

    return (
        <>
            {!isLoading && children}
        </>
    )
}