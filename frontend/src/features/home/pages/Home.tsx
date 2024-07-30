import { useEffect, useState } from "react"
import HomeTabGroup from "../components/HomeTabGroup"
import PopularFeed from "../components/PopularFeed"
import FollowingFeed from "../components/FollowingFeed"

const Home = () => {
    const [selectedTabIndex, setSelectedIndex] = useState(0)

    useEffect(() => {
        document.title = "Home / Y"
    })

    const onTabSelected = (index: number) => {
        setSelectedIndex(index)
    }

    return (
        <div className="w-full h-full">
            <div className="w-full flex">
                <HomeTabGroup labelNames={["Popular", "Following"]} onChange={onTabSelected} />
                {selectedTabIndex === 0 && <PopularFeed />}
                {selectedTabIndex === 1 && <FollowingFeed />}
            </div>
        </div>
    )
}

export default Home