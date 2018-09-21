/* eslint "jsx-a11y/accessible-emoji": 0 */
import React from "react"
import { Link } from "react-router-dom"
import { css } from "emotion"

import blog from "../blog"
import BasicLayout from "../layouts/BasicLayout"

import BlogNoEntryPage from "./BlogNoEntryPage"
import GoToBuilderButton from "./components/GoToBuilderButton"
import ToggleMap from "./components/ToggleMap"

const subtitleClassName = css`
  font-size: 0.8rem;
`

const footerClassName = css`
  display: flex;
  justify-content: space-between;
`

const BlogEntryPage = ({ match }) => {
  const entryIndex = blog.findIndex(e => e.id === match.params.entryId)
  const entry = blog[entryIndex]
  const nextEntry = blog[entryIndex - 1]
  const prevEntry = blog[entryIndex + 1]

  if (!entry) return <BlogNoEntryPage />

  return (
    <BasicLayout
      breadCrumb={
        <span>
          > <Link to="/blog">Blog</Link> > {entry.title}
        </span>
      }
      footer={
        <div className={footerClassName}>
          <div>
            {prevEntry && (
              <Link to={`/blog/${prevEntry.id}`}>« {prevEntry.title}</Link>
            )}
          </div>
          <div>
            {nextEntry && (
              <Link to={`/blog/${nextEntry.id}`}>{nextEntry.title} »</Link>
            )}
          </div>
        </div>
      }
    >
      <h1>
        <small className={subtitleClassName}>
          📅 {new Date(entry.date).toISOString().slice(0, 10)}
        </small>
        <br />
        {entry.title}
      </h1>
      <p>{entry.tagline}</p>
      <br />
      {entry.map && <ToggleMap map={entry.map} />}
      {entry.world && <GoToBuilderButton world={entry.world} />}
      <br />
      <br />
      <entry.Component />
    </BasicLayout>
  )
}

export default BlogEntryPage
