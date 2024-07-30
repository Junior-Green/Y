import { useQuery } from "@tanstack/react-query";
import { getAuthenticatedUser } from "@/utils/api";

export const useUser = () => {
    const { data, isLoading, error } = useQuery({
        queryKey: ['user', 'authenticated', 'GET'],
        queryFn: getAuthenticatedUser,
        staleTime: 0,
        retry: 3,
    })

    return { user: data, isLoading, error };
}
