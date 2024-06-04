import { useQuery } from "@tanstack/react-query";
import { getAuthenticatedUser } from "@/utils/api";

export const useUser = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['user', 'authenticated', 'GET'],
        queryFn: getAuthenticatedUser,
    })

    return { data, error, isLoading };
}
