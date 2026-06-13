import { useEffect, useRef } from "react";
import Chart from "chart.js/auto";
import { Card } from "react-bootstrap";

export default function Graphic({ details }: { details: { name: string; value: number }[] }) {
    const canvasRef = useRef<HTMLCanvasElement | null>(null);
    const chartRef = useRef<Chart | null>(null);

    useEffect(() => {
        if (canvasRef.current && details.length > 0) {
            if (chartRef.current) chartRef.current.destroy(); 

            chartRef.current = new Chart(canvasRef.current, {
                type: "pie",
                data: {
                    labels: details.map(d => d.name),
                    datasets: [{
                        data: details.map(d => d.value),
                        backgroundColor: ["#3b82f6", "#14b8a6", "#f59e0b", "#f43f5e"] 
                    }]
                },
                options: { responsive: true, maintainAspectRatio: false }
            });
        }
        return () => { if (chartRef.current) chartRef.current.destroy(); }; 
    }, [details]);

    return (
        <Card className="shadow-sm border rounded-3 overflow-hidden w-100">
            <Card.Header className="bg-light text-muted fs-6">
                Total de objetos en el museo (por sección):
            </Card.Header>
            <Card.Body style={{ height: "300px", position: "relative" }}>
                <canvas ref={canvasRef}></canvas>
            </Card.Body>
        </Card>
    );
}