import { useMemo } from "react"
import { Link } from "react-router-dom"

export interface ParagraphProps {
    children: string
}


const Paragraph = ({ children }: ParagraphProps) => {
    const words = useMemo(() => {
        return children.split(" ")
    }, [children])

    return (
        <p>
            {
                words.map((word, i) => {
                    if (word.at(0) === "#" && word.length > 1) {
                        return <Link className="text-y-accent-blue hover:underline decoration-y-accent-blue" to={`/hashtag/${word.substring(1)}`}>{`${word}${i == word.length - 1 ? "" : " "}`}</Link>
                    }
                    if (word.at(0) === "@" && word.length > 1) {
                        return <Link className="text-y-accent-blue hover:underline decoration-y-accent-blue" to={`/${word.substring(1)}`}>{`${word}${i == word.length - 1 ? "" : " "}`}</Link>
                    }
                    return <span>{`${word}${i == word.length - 1 ? "" : " "}`}</span>
                })
            }
        </p>
    )
}

export default Paragraph