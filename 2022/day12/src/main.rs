use std::fs;

struct Pos {
    val: (isize, isize),
    step: u32,
}

fn part2(data: &str) -> u32 {
    let mut grid: Vec<Vec<char>> = data.lines().map(|i| i.chars().collect()).collect();
    let mut starts = Vec::new();
    let mut end = (0, 0);
    for i in 0..grid.len() {
        for j in 0..grid[i].len() {
            if grid[i][j] == 'S' {
                grid[i][j] = 'a';
            } else if grid[i][j] == 'E' {
                end = (i as isize, j as isize);
                grid[i][j] = 'z';
            }
            if grid[i][j] == 'a' {
                starts.push((i as isize, j as isize));
            }
        }
    }
    let mut dists: Vec<u32> = Vec::new();
    for k in starts {
        let dirs: [(isize, isize); 4] = [(0, 1), (1, 0), (0, -1), (-1, 0)];
        let mut queue: Vec<Pos> = vec![Pos { val: k, step: 0 }];
        let mut prev: Vec<(isize, isize)> = Vec::new();
        while !queue.is_empty() {
            let cur = queue.remove(0);
            if cur.val == end {
                dists.push(cur.step);
                break;
            }
            if prev.contains(&cur.val) {
                continue;
            }
            prev.push(cur.val);
            for i in dirs {
                if cur.val.0 + i.0 < 0
                    || cur.val.0 + i.0 >= grid.len() as isize
                    || cur.val.1 + i.1 < 0
                    || cur.val.1 + i.1 >= grid[(cur.val.0 + i.0) as usize].len() as isize
                    || (grid[(cur.val.0 + i.0) as usize][(cur.val.1 + i.1) as usize] as isize)
                        - (grid[cur.val.0 as usize][cur.val.1 as usize] as isize)
                        > 1
                {
                    continue;
                }
                queue.push(Pos {
                    val: (cur.val.0 + i.0, cur.val.1 + i.1),
                    step: cur.step + 1,
                });
            }
        }
    }
    *dists.iter().min().unwrap()
}

fn part1(data: &str) -> u32 {
    let mut grid: Vec<Vec<char>> = data.lines().map(|i| i.chars().collect()).collect();
    let mut start = (0, 0);
    let mut end = (0, 0);
    for i in 0..grid.len() {
        for j in 0..grid[i].len() {
            if grid[i][j] == 'S' {
                start = (i as isize, j as isize);
                grid[i][j] = 'a';
            } else if grid[i][j] == 'E' {
                end = (i as isize, j as isize);
                grid[i][j] = 'z';
            }
        }
    }
    let dirs: [(isize, isize); 4] = [(0, 1), (1, 0), (0, -1), (-1, 0)];
    let mut queue: Vec<Pos> = vec![Pos {
        val: start,
        step: 0,
    }];
    let mut prev: Vec<(isize, isize)> = Vec::new();
    while !queue.is_empty() {
        let cur = queue.remove(0);
        if cur.val == end {
            return cur.step;
        }
        if prev.contains(&cur.val) {
            continue;
        }
        prev.push(cur.val);
        for i in dirs {
            if cur.val.0 + i.0 < 0
                || cur.val.0 + i.0 >= grid.len() as isize
                || cur.val.1 + i.1 < 0
                || cur.val.1 + i.1 >= grid[(cur.val.0 + i.0) as usize].len() as isize
                || (grid[(cur.val.0 + i.0) as usize][(cur.val.1 + i.1) as usize] as isize)
                    - (grid[cur.val.0 as usize][cur.val.1 as usize] as isize)
                    > 1
            {
                continue;
            }
            queue.push(Pos {
                val: (cur.val.0 + i.0, cur.val.1 + i.1),
                step: cur.step + 1,
            });
        }
    }
    0
}

fn main() {
    let data = fs::read_to_string("day12.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
