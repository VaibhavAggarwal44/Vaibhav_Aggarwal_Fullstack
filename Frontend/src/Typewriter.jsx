import React from 'react';
//importing typewriter-effect
import Typewriter from "typewriter-effect";
import './App.css';

function f(){

    let s="Welcome to Blogger app"
    let s2="Create the blogs for world to see"
    let s3="Keep your documents protected"
    let s4="Search on the basis of keywords"
    return (
    // <Loop>
    <Typewriter

        onInit={(typewriter) => {
            typewriter
                // .loop(true)
                .typeString(s)
                .pauseFor(200)
                .deleteAll()
                .typeString(s2)
                .pauseFor(200)
                .deleteAll()
                .typeString(s3)
                .pauseFor(200)
                .deleteAll()
                .typeString(s4)
                .pauseFor(200)
                .deleteAll()
                .start({loop:true})  

        }}

        options={{
            autoStart: true,
            loop: true,
          }}
        
    />
    // </Loop>
    )
}

function Typewriterj() {

	return (
		<div className="Typewriter">
            {/* {setTimeout(f(), 6000)} */}
			{
                
                f()
            }
		</div>
	);
}

export default Typewriterj;
