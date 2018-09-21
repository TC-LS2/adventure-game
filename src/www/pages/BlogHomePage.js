import React from "react"
import { Link } from "react-router-dom"

import blog from "../blog"
import BasicLayout from "../layouts/BasicLayout"

const BlogHomePage = () => (
  <BasicLayout breadCrumb="> Blog">
    <ul>
      {blog.map(entry => (
        <li key={entry.id}>
          <Link to={`/blog/${entry.id}`}>{entry.title}</Link>
        </li>
      ))}
    </ul>
  </BasicLayout>
)

export default BlogHomePage
