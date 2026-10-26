const express = require("express")

const app = express()
const port = process.env.PORT || 3000

let ready = true;


app.get("/",(req,res)=>{
    res.json({
        application: "nodejs-deployment-lab",
        version: process.env.APP_VERSION || "v1",
        hostname: process.env.HOSTNAME
    })
})

app.get("/health",(req,res)=>{
    res.status(200).json({
        status: "healthy"
    })
})

app.get("/ready",(req,res)=>{
    if(!ready){
        return res.status(503).json({
            status: "not ready"
        })
    }
    res.status(200).json({
        status: "ready"
    })
})

app.get("/not-ready",(req,res)=>{
    ready = false;
    res.status(200).json({
        status: "not ready"
    })
})

app.get("/ready-again",(req,res)=>{
    ready = true;
    res.status(200).json({
        status: "ready"
    })
})

app.get("/crash",(req,res)=>{
    res.send("crashing....")
    process.exit(1)
})

app.get("/slow",async(req,res)=>{
    await new Promise(resolve => setTimeout(resolve, 10000));
    res.json({
        message: "Slow response completed after 10 seconds"
    })
})

app.listen(port, () => {
    console.log(`Server is running on port ${port}`)
})