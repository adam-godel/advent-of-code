use std::fs;

fn part2(grid: &Vec<Vec<char>>, instr: &Vec<&str>) -> usize {
    let (mut ci, mut cj, mut dir) = (
        0isize,
        grid[0].iter().position(|&i| i == '.').unwrap() as isize,
        0isize,
    );
    let dirs = [(0, 1), (1, 0), (0, -1), (-1, 0)];
    for i in instr {
        if let Ok(i) = i.parse::<isize>() {
            for _ in 0..i {
                if ci + dirs[dir as usize].0 < 0
                    || ci + dirs[dir as usize].0 >= grid.len() as isize
                    || cj + dirs[dir as usize].1 < 0
                    || cj + dirs[dir as usize].1 >= grid[ci as usize].len() as isize
                    || grid[(ci + dirs[dir as usize].0) as usize]
                        [(cj + dirs[dir as usize].1) as usize]
                        == ' '
                {
                    let (oci, ocj, odir) = (ci, cj, dir);
                    match dir {
                        0 => {
                            (ci, cj, dir) = match ci {
                                0..50 => (149 - ci, 99, 2),
                                50..100 => (49, ci + 50, 3),
                                100..150 => (149 - ci, 149, 2),
                                150..200 => (149, ci - 100, 3),
                                _ => (-1, -1, -1),
                            }
                        }
                        1 => {
                            (ci, cj, dir) = match cj {
                                0..50 => (0, cj + 100, 1),
                                50..100 => (cj + 100, 49, 2),
                                100..150 => (cj - 50, 99, 2),
                                _ => (-1, -1, -1),
                            }
                        }
                        2 => {
                            (ci, cj, dir) = match ci {
                                0..50 => (149 - ci, 0, 0),
                                50..100 => (100, ci - 50, 1),
                                100..150 => (149 - ci, 50, 0),
                                150..200 => (0, ci - 100, 1),
                                _ => (-1, -1, -1),
                            }
                        }
                        3 => {
                            (ci, cj, dir) = match cj {
                                0..50 => (cj + 50, 50, 0),
                                50..100 => (cj + 100, 0, 0),
                                100..150 => (199, cj - 100, 3),
                                _ => (-1, -1, -1),
                            }
                        }
                        _ => (),
                    }
                    if grid[ci as usize][cj as usize] == '#' {
                        (ci, cj, dir) = (oci, ocj, odir);
                    }
                } else if grid[(ci + dirs[dir as usize].0) as usize]
                    [(cj + dirs[dir as usize].1) as usize]
                    == '.'
                {
                    ci += dirs[dir as usize].0;
                    cj += dirs[dir as usize].1;
                }
            }
        } else if *i == "L" {
            dir = (dir - 1).rem_euclid(4);
        } else if *i == "R" {
            dir = (dir + 1).rem_euclid(4);
        }
    }
    (1000 * (ci + 1) + 4 * (cj + 1) + dir) as usize
}

fn part1(grid: &Vec<Vec<char>>, instr: &Vec<&str>) -> usize {
    let (mut ci, mut cj, mut dir) = (
        0isize,
        grid[0].iter().position(|&i| i == '.').unwrap() as isize,
        0isize,
    );
    let dirs = [(0, 1), (1, 0), (0, -1), (-1, 0)];
    for i in instr {
        if let Ok(i) = i.parse::<isize>() {
            for _ in 0..i {
                if ci + dirs[dir as usize].0 < 0
                    || ci + dirs[dir as usize].0 >= grid.len() as isize
                    || cj + dirs[dir as usize].1 < 0
                    || cj + dirs[dir as usize].1 >= grid[ci as usize].len() as isize
                    || grid[(ci + dirs[dir as usize].0) as usize]
                        [(cj + dirs[dir as usize].1) as usize]
                        == ' '
                {
                    let (oci, ocj) = (ci, cj);
                    match dir {
                        0 => {
                            cj = grid[ci as usize].iter().position(|&i| i != ' ').unwrap() as isize
                        }
                        1 => {
                            ci = grid
                                .iter()
                                .map(|i| {
                                    i.iter()
                                        .enumerate()
                                        .filter(|&(j, _)| j == cj as usize)
                                        .next()
                                        .unwrap()
                                        .1
                                })
                                .position(|&i| i != ' ')
                                .unwrap() as isize
                        }
                        2 => {
                            cj = grid[ci as usize].iter().rposition(|&i| i != ' ').unwrap() as isize
                        }
                        3 => {
                            ci = grid
                                .iter()
                                .map(|i| {
                                    i.iter()
                                        .enumerate()
                                        .filter(|&(j, _)| j == cj as usize)
                                        .next()
                                        .unwrap()
                                        .1
                                })
                                .rposition(|&i| i != ' ')
                                .unwrap() as isize
                        }
                        _ => (),
                    }
                    if grid[ci as usize][cj as usize] == '#' {
                        (ci, cj) = (oci, ocj);
                    }
                } else if grid[(ci + dirs[dir as usize].0) as usize]
                    [(cj + dirs[dir as usize].1) as usize]
                    == '.'
                {
                    ci += dirs[dir as usize].0;
                    cj += dirs[dir as usize].1;
                }
            }
        } else if *i == "L" {
            dir = (dir - 1).rem_euclid(4);
        } else if *i == "R" {
            dir = (dir + 1).rem_euclid(4);
        }
    }
    (1000 * (ci + 1) + 4 * (cj + 1) + dir) as usize
}

fn main() {
    let data = fs::read_to_string("day22.txt").unwrap();
    let mut iter = data.lines();
    let mut line = iter.next().unwrap();
    let mut grid: Vec<Vec<char>> = vec![line.chars().collect()];
    line = iter.next().unwrap();
    while line.len() > 0 {
        let mut next: Vec<char> = line.chars().collect();
        next.resize(grid[0].len(), ' ');
        grid.push(next);
        line = iter.next().unwrap();
    }
    let line = &iter.next().unwrap().replace("L", " L ").replace("R", " R ");
    let instr: Vec<&str> = line.split_whitespace().collect();
    println!("{}\n{}", part1(&grid, &instr), part2(&grid, &instr));
}
