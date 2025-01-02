use std::fs;

fn part2(data: &str) -> u32 {
    let grid: Vec<Vec<char>> = data.lines().map(|i| i.chars().collect()).collect();
    let mut max = 0;
    for i in 0..grid.len() {
        for j in 0..grid[i].len() {
            let mut iu = 0;
            for k in (0..i).rev() {
                iu += 1;
                if grid[k][j] >= grid[i][j] {
                    break;
                }
            }
            let mut il = 0;
            for k in i+1..grid.len() {
                il += 1;
                if grid[k][j] >= grid[i][j] {
                    break;
                }
            }
            let mut ju = 0;
            for k in (0..j).rev() {
                ju += 1;
                if grid[i][k] >= grid[i][j] {
                    break;
                }
            }
            let mut jl = 0;
            for k in j+1..grid[i].len() {
                jl += 1;
                if grid[i][k] >= grid[i][j] {
                    break;
                }
            }
            let result = iu*il*ju*jl;
            if result > max {
                max = result;
            }
        }
    }
    max
}

fn part1(data: &str) -> u32 {
    let grid: Vec<Vec<char>> = data.lines().map(|i| i.chars().collect()).collect();
    let mut result = 0;
    for i in 0..grid.len() {
        for j in 0..grid[i].len() {
            let mut iu = true;
            for k in 0..i {
                if grid[k][j] >= grid[i][j] {
                    iu = false;
                }
            }
            let mut il = true;
            for k in i+1..grid.len() {
                if grid[k][j] >= grid[i][j] {
                    il = false;
                }
            }
            let mut ju = true;
            for k in 0..j {
                if grid[i][k] >= grid[i][j] {
                    ju = false;
                }
            }
            let mut jl = true;
            for k in j+1..grid[i].len() {
                if grid[i][k] >= grid[i][j] {
                    jl = false;
                }
            }
            if iu || il || ju || jl {
                result += 1;
            }
        }
    }
    result
}

fn main() {
    let data = fs::read_to_string("day8.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}