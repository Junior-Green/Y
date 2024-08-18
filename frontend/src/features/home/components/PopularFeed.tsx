import LoadingIndicator from "@/components/LoadingIndicator"
import PostDisplay from "@/components/PostDisplay"
import { getPaginatedPostsByLikes } from "@/utils/api"
import { Post } from "@/utils/types"
import { useInfiniteQuery } from "@tanstack/react-query"
import { useCallback, useEffect, useRef } from "react"


const PopularFeed = () => {
    const scrollableRef = useRef<HTMLDivElement | null>(null)

    const {
        data,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
        isLoading
    } = useInfiniteQuery({
        queryKey: ['post', 'popular', 'paginated'],
        initialPageParam: 0,
        staleTime: Infinity,
        queryFn: getPaginatedPostsByLikes,
        getNextPageParam: (lastPage) => {
            return lastPage.nextPage === null ? undefined : lastPage.nextPage
        },
    })

    const handleScroll = useCallback(() => {
        console.log(isFetchingNextPage, hasNextPage)
        const scrollPosition = (scrollableRef.current!.scrollHeight) + 170 - (scrollY + window.innerHeight)
        if (scrollPosition <= 300 && !isFetchingNextPage && hasNextPage) {
            fetchNextPage()
        }
    }, [isFetchingNextPage, hasNextPage])

    useEffect(() => {
        // Add scroll event listener to the window
        window.addEventListener('scroll', handleScroll);

        // Remove scroll event listener on cleanup
        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    }, [handleScroll]);


    return (
        <div className="w-full flex flex-col items-center overflow-hidden" ref={scrollableRef}>
            {
                data?.pages
                    .reduce((prev, curr) => prev.concat(curr.data ?? []), new Array<Post>())
                    .map((post, i) => <PostDisplay key={i} post={post} />)
            }
            {(isLoading || isFetchingNextPage) && <div className="my-8"> <LoadingIndicator size={30} /></div>}
        </div>
    )
}

export default PopularFeed