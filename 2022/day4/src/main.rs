use std::fs;

fn part2(data: &str) -> u32 {
    data.lines()
        .map(|i| {
            let f: Vec<Vec<u32>> = i
                .split(",")
                .map(|k| {
                    k.split("-")
                        .collect::<Vec<&str>>()
                        .into_iter()
                        .map(|k| k.parse().unwrap())
                        .collect()
                })
                .collect();
            ((f[0][0] <= f[1][0] && f[0][1] >= f[1][0])
                || (f[1][0] <= f[0][0] && f[1][1] >= f[0][0])) as u32
        })
        .sum()
}

fn part1(data: &str) -> u32 {
    data.lines()
        .map(|i| {
            let f: Vec<Vec<u32>> = i
                .split(",")
                .map(|k| {
                    k.split("-")
                        .collect::<Vec<&str>>()
                        .into_iter()
                        .map(|k| k.parse().unwrap())
                        .collect()
                })
                .collect();
            ((f[0][0] >= f[1][0] && f[0][1] <= f[1][1])
                || (f[1][0] >= f[0][0] && f[1][1] <= f[0][1])) as u32
        })
        .sum()
}

fn main() {
    let data = fs::read_to_string("day4.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
