import { useUser } from "@/hooks/hooks";
import React, { useEffect } from "react";
import { redirect, useNavigate } from "react-router-dom";

interface AuthenticatedRouteProps {
    children: React.ReactNode
}

export default function AuthenticatedRoute({ children }: AuthenticatedRouteProps) {
    const { user, error, isLoading } = useUser()
    const navigate = useNavigate()
    
    useEffect(() => {
        
        if (error || !user) {
            navigate("/") 
        }
    }, [isLoading, error, user])

    return (
        <>
            {!isLoading && children}
        </>
    )
}