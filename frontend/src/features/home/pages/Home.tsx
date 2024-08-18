import { useEffect, useState } from "react"
import HomeTabGroup from "../components/HomeTabGroup"
import PopularFeed from "../components/PopularFeed"
import FollowingFeed from "../components/FollowingFeed"
import CreatePostWidget from "../../../components/CreatePostWidget"

const Home = () => {
    const [selectedTabIndex, setSelectedIndex] = useState(0)

    useEffect(() => {
        document.title = "Home / Y"
    })

    const onTabSelected = (index: number) => {
        setSelectedIndex(index)
    }

    return (
        <div className="w-full h-auto flex flex-col  `  ">
            <div className="w-full flex sticky top-0 z-10">
                <HomeTabGroup labelNames={["Popular", "Following"]} onChange={onTabSelected} />
            </div>
            <CreatePostWidget />
            {selectedTabIndex === 0 && <PopularFeed />}
            {selectedTabIndex === 1 && <FollowingFeed />}
        </div>
    )
}

export default Home