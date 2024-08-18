import { useQuery } from "@tanstack/react-query";
import { getAuthenticatedUser, getTimeNow } from "@/utils/api";

export const useUser = () => {
    const { data, isLoading, error, isSuccess } = useQuery({
        queryKey: ['user', 'authenticated', 'GET'],
        queryFn: getAuthenticatedUser,
        staleTime: 1000 * 60 * 5,
        retry: 3,
    })

    return { user: data, isLoading, error, isSuccess };
}

export const useDate = () => {
    const { data, isLoading, isFetching, error } = useQuery({
        queryKey: ['date', 'now'],
        queryFn: getTimeNow,
        staleTime: 1000 * 60 * 10,
        placeholderData: new Date(Date.now()),
        retry: 3,
    })

    return { date: data, isLoading, isFetching, error }
}
