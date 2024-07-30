import { useState } from "react"

interface HomeTabGroupProps {
    labelNames: string[]
    onChange: (index: number) => void
}

interface HomeTabProps {
    tabName: string
    focused: boolean
}

const HomeTabGroup = ({ labelNames, onChange }: HomeTabGroupProps) => {
    const [selectedIndex, setSelectedIndex] = useState(0)

    const onTabSelected = (index: number) => {
        setSelectedIndex(index)
        onChange(index)
    }

    return (
        <>
            {labelNames.map((label, i) => {
                return <div className="w-full h-full" onClick={() => onTabSelected(i)}><HomeTab tabName={label} focused={i === selectedIndex} /></div>
            })}
        </>
    )
}

const HomeTab = ({ tabName, focused }: HomeTabProps) => {
    return (
        <div className="w-full h-full border-b-[1px] border-b-y-gray-400 hover:cursor-pointer transition-colors hover:bg-opacity-50 hover:bg-y-gray-400 pt-3">
            <div className="w-min h-full mx-auto">
                <span style={{ color: focused ? "white" : "#71767C", fontWeight: focused ? 700 : 600 }}>{tabName}</span>
                <hr style={{ visibility: focused ? "visible" : "hidden" }} className="border-t-y-accent-blue border-t-[4px] rounded-full mt-3"></hr>
            </div>
        </div>
    )
}

export default HomeTabGroup