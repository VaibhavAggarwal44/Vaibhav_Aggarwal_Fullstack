import React from 'react'
import "./Footer.css"

function Footer() {
  return (
    <footer className="footer-distributed">

			<div className="footer-right">

				<a href="#"><i className="fa fa-facebook"></i></a>
				<a href="#"><i className="fa fa-twitter"></i></a>
				<a href="#"><i className="fa fa-linkedin"></i></a>
				<a href="#"><i className="fa fa-github"></i></a>

			</div>

			<div className="footer-left">

				<p className="footer-links">
					<a className="link-1" href="#">Home</a>

					<a href="#">Blog</a>

					<a href="#">Pricing</a>

					<a href="#">About</a>

					<a href="#">Faq</a>

					<a href="#">Contact</a>
				</p>

				<p>Company Name &copy; 2015</p>
			</div>

		</footer>
  )
}

export default Footer